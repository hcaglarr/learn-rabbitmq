package com.hcaglar.threadchannelconnectionfactory.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

/**
 * @author Hüseyin ÇAĞLAR
 * @version 1.0
 * @since 3.03.2022
 */
@Service
public class MainTextService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainTextService.class);
    private final TextService m_textService;
    private final UpperCaseTextService m_upperCaseTextService;
    private final TaskExecutor m_exec;

    public MainTextService(TextService textService, UpperCaseTextService upperCaseTextService, TaskExecutor exec) {
        m_textService = textService;
        m_upperCaseTextService = upperCaseTextService;
        m_exec = exec;
    }

    public void send(String in){
        LOGGER.info(String.format("%s send(%s)",getClass(), in));
        m_textService.send(in);
        m_exec.execute(() -> m_upperCaseTextService.send(in));
    }
}
