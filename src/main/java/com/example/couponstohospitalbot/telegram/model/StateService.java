package com.example.couponstohospitalbot.telegram.model;

import com.example.couponstohospitalbot.telegram.hospitalCommand.HospitalCommandName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class StateService {
    private final StateRepository stateRepository;
    private static final Logger logger = Logger.getLogger(StateService.class.getName());

    @Transactional
    public HospitalCommandName getCurrentState(Long chatId) {
        Optional<State> optionalState = stateRepository.findByChatId(chatId);
        if (optionalState.isEmpty()) {
            logger.warning("current state is unknown");
            return null;
        }
        State state = optionalState.get();
        if (state.getRegionId() == null) {
            return HospitalCommandName.REGION;
        }
        if (state.getHospitalId() == null) {
            return HospitalCommandName.HOSPITAL;
        }
        if (state.getDirectionId() == null) {
            return HospitalCommandName.DIRECTION;
        }
        if (state.getDoctorId() == null) {
            return HospitalCommandName.DOCTOR;
        }
        return HospitalCommandName.NO;
    }

    @Transactional
    public void saveRegion(Long chatId, String regionId) {
        Optional<State> optionalState = stateRepository.findByChatId(chatId);
        if (optionalState.isEmpty()) {
            logger.info("can't save region - chat is empty");
            return;
        }
        State state = optionalState.get();
        state.setRegionId(regionId);
        stateRepository.save(state);
    }

    @Transactional
    public void saveHospital(Long chatId, Integer hospitalId) {
        Optional<State> optionalState = stateRepository.findByChatId(chatId);
        if (optionalState.isEmpty()) {
            logger.info("can't save hospital - chat is empty");
            return;
        }
        State state = optionalState.get();
        state.setHospitalId(hospitalId);
        stateRepository.save(state);
    }

    @Transactional
    public void saveDirection(Long chatId, String directionId) {
        Optional<State> optionalState = stateRepository.findByChatId(chatId);
        if (optionalState.isEmpty()) {
            logger.info("can't save direction - chat is empty");
            return;
        }
        State state = optionalState.get();
        state.setDirectionId(directionId);
        stateRepository.save(state);
    }

    @Transactional
    public void saveDoctor(Long chatId, String doctorId) {
        Optional<State> optionalState = stateRepository.findByChatId(chatId);
        if (optionalState.isEmpty()) {
            logger.info("can't save direction - chat is empty");
            return;
        }
        State state = optionalState.get();
        state.setDoctorId(doctorId);
        stateRepository.save(state);
    }


    public State findByChatId(Long chatId) {
        Optional<State> state = stateRepository.findByChatId(chatId);
        return state.orElse(null);
    }

    @Transactional
    public void saveChat(Long chatId) {
        stateRepository.save(new State(chatId));
    }
}