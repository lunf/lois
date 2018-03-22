package com.lunf.delilah.config;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lunf.delilah.domain.MessageEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class DisruptorConfig {

    @Value("${ring_buffer_size}")
    private int ringBufferSize;

    @Value("${num_available_threads}")
    private int threadCount;

    @Bean
    public RingBuffer<MessageEvent> ringBuffer() {
        return RingBuffer.createMultiProducer(MessageEvent.EVENT_FACTORY, ringBufferSize);
    }

    @Bean
    public SequenceBarrier sequenceBarrier() {
        return ringBuffer().newBarrier();
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(threadCount);
    }
}
