package com.demo.calculator.service.impl;

import com.demo.calculator.service.UserRecordService;
import com.demo.calculator.exception.RecordNotFoundException;
import com.demo.calculator.persistence.repository.UserRepository;
import com.demo.calculator.response.UserRecordDTO;
import com.demo.calculator.response.UserRecordPageDTO;
import com.demo.calculator.persistence.model.Operation;
import com.demo.calculator.persistence.model.User;
import com.demo.calculator.persistence.model.UserRecord;
import com.demo.calculator.persistence.repository.UserRecordRepository;
import static com.demo.calculator.persistence.specification.UserRecordSpecification.*;

import com.demo.calculator.service.AuthenticationFacade;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserRecordServiceImpl implements UserRecordService {

    private final ModelMapper modelMapper;
    private final UserRecordRepository userRecordRepository;

    private final UserRepository userRepository;

    private final MessageSource messageSource;

    private final AuthenticationFacade authenticationFacade;

    public UserRecordServiceImpl(UserRecordRepository userRecordRepository,
                                 UserRepository userRepository, ModelMapper modelMapper,
                                 MessageSource messageSource,
                                 AuthenticationFacade authenticationFacade) {
        this.userRecordRepository = userRecordRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.messageSource = messageSource;
        this.authenticationFacade = authenticationFacade;
    }

    /**
     * Find all user records
     *
     * @param operation
     * @param startDate
     * @param endDate
     * @param sortBy
     * @param sortOrder
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public UserRecordPageDTO findAll(Operation operation, LocalDate startDate,
                                       LocalDate endDate, String sortBy, String sortOrder, int page, int pageSize) {

        Long userId = authenticationFacade.getUserId();
        Specification<UserRecord> spec = Specification.where(filterByUser(userId));

        if (operation != null) {
            spec = spec.and(filterByOperation(operation));
        }

        if (startDate != null) {
            spec = spec.and(filterByDateStart(startDate));
        }

        if (endDate != null) {
            spec = spec.and(filterByDateEnd(endDate));
        }

        Sort sort = Sort.by( (sortOrder != null && sortOrder.trim().equals("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable paging = PageRequest.of(page, pageSize, sort);
        Page<UserRecord> resultsPage = userRecordRepository.findAll(spec, paging);
        List<UserRecordDTO> results = resultsPage.getContent().stream()
                .map(e -> modelMapper.map(e, UserRecordDTO.class))
                .collect(Collectors.toList());

        results.stream()
                .filter(a -> a.getOperationType() != null)
                .forEach(a -> {
            a.setOperationName(messageSource.getMessage(a.getOperationType().toString(), null, Locale.getDefault()));
        });

        return new UserRecordPageDTO(results, resultsPage.getTotalElements(), resultsPage.getTotalPages());

    }

    /**
     * Soft-delete a user record and restore used amount on user balance
     *
     * @param id
     */
    @Transactional
    public void delete(Long id) {

        Long userId = authenticationFacade.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException(userId.toString()));

        // restore balance
        UserRecord lastUserRecord = getLastUserRecord()
                .orElseThrow(() -> new RecordNotFoundException("userId: " + userId));

        // prevent to update already deleted record
        // and a record that's doesn't belong to him
        /*
        .filter(i -> i.isSuccess()
                && !i.isDeleted()
                && i.getOperation() != null
                && i.getUser().getId() == userId) // prevent */
        UserRecord deletedUserRecord = userRecordRepository.find(userId, id)
                .map(i -> {
                    i.setUserDeleted(user);
                    i.setDateDeleted(LocalDateTime.now());
                    i.setDeleted(true);
                    userRecordRepository.save(i);
                    return i;
                })
                .orElseThrow(() -> new RecordNotFoundException(id.toString()));

        UserRecord newUserRecord = new UserRecord();
        newUserRecord.setUser(lastUserRecord.getUser());
        newUserRecord.setUserBalance(lastUserRecord.getUserBalance() + deletedUserRecord.getAmount());
        newUserRecord.setDate(LocalDateTime.now());
        newUserRecord.setAmount(deletedUserRecord.getAmount());
        newUserRecord.setOperationResponse("Restore user balance after delete record #" + id);
        newUserRecord.setSuccess(true);
        userRecordRepository.save(newUserRecord);

    }

    @Override
    public Optional<UserRecord> getLastUserRecord() {
        Long userId = authenticationFacade.getUserId();
        Specification<UserRecord> spec = Specification.where(filterByUser(userId)).and(filterByValid());
        Page<UserRecord> userRecordPage = userRecordRepository.findAll(spec,
                PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "dateUts")));
        return !userRecordPage.isEmpty() ? userRecordPage.getContent().stream().findFirst() : Optional.empty();
    }

}
