package com.lunf.lois.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lunf.lois.controller.request.DeviceRequest;
import com.lunf.lois.utilities.RandomString;
import com.lunf.lois.LoisApplication;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BenchmarkHarnessTests {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Test
    public void
    launchBenchmark() throws Exception {

        Options opt = new OptionsBuilder()
                // Specify which benchmarks to run.
                // You can be more specific if you'd like to run only one benchmark per test.
                .include(this.getClass().getName() + ".*")
                // Set the following options as needed
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.MILLISECONDS)
                .warmupTime(TimeValue.seconds(1))
                .warmupIterations(2)
                .measurementTime(TimeValue.seconds(1))
                .measurementIterations(2)
                .threads(2)
                .forks(1)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                //.jvmArgs("-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintInlining")
                //.addProfiler(WinPerfAsmProfiler.class)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    public void asyncWithSleuth(BenchmarkContext context) throws Exception {
        performRequest(context.mockMvcForTracedController);
    }

    private void performRequest(MockMvc mockMvc) throws Exception {
//        MvcResult mvcResult = mockMvc.perform(get("/" + url))
//                .andExpect(status().isOk())
//                .andExpect(request().asyncStarted())
//                .andReturn();
//
//        mockMvc.perform(asyncDispatch(mvcResult))
//                .andExpect(status().isOk())
//                .andExpect(content().string(expectedResult));

        String notificationId = new RandomString.Builder().nextString();
        ZonedDateTime now = ZonedDateTime.now();
        String year = String.valueOf(now.getYear());

        DeviceRequest deviceRequest = new DeviceRequest();
        deviceRequest.setNotification_id(notificationId);

        String jsonInString = mapper.writeValueAsString(deviceRequest);

        mockMvc.perform(post("/devices")
                .header(HttpHeaders.AUTHORIZATION, "Bearer UmfyL4aBCK11tNgEs5CcjC4kv31nFI6Q")
                .contentType(contentType)
                .content(jsonInString))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.notification_id", is(notificationId)))
                .andExpect(jsonPath("$.created_at", containsString(year)));
    }

    @State(Scope.Benchmark)
    public static class BenchmarkContext {
        volatile ConfigurableApplicationContext withSleuth;
        volatile DummyFilter dummyFilter = new DummyFilter();
        volatile MockMvc mockMvcForTracedController;


        @Setup
        public void setup() {
            this.withSleuth = new SpringApplication(
                    LoisApplication.class)
                    .run("--spring.jmx.enabled=false",
                            "--spring.application.name=withSleuth");
            this.mockMvcForTracedController = MockMvcBuilders.standaloneSetup(
                    this.withSleuth.getBean(DeviceController.class))
                    .build();

        }

        @TearDown
        public void clean() {
            this.withSleuth.close();
        }

    }

    private static class DummyFilter implements Filter {

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response,
                             FilterChain chain) throws IOException, ServletException {
            chain.doFilter(request, response);
        }

        @Override
        public void destroy() {
        }
    }


}
