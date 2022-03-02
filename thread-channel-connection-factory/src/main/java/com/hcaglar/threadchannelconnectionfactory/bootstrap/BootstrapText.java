package com.hcaglar.threadchannelconnectionfactory.bootstrap;

import com.hcaglar.threadchannelconnectionfactory.service.MainTextService;
import com.hcaglar.threadchannelconnectionfactory.service.UpperCaseTextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Hüseyin ÇAĞLAR
 * @version 1.0
 * @since 3.03.2022
 */
@Component
public class BootstrapText {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpperCaseTextService.class);
    private final MainTextService m_textService;
    private final TaskExecutor m_exec;

    public BootstrapText(MainTextService textService, TaskExecutor exec) {
        m_textService = textService;
        m_exec = exec;
    }

    @PostConstruct
    public void init(){
        var in = "Hello RabbitMQ";
        LOGGER.info(String.format("%s send(%s)",getClass(), in));
        m_exec.execute(() -> m_textService.send(in));
    }
}
