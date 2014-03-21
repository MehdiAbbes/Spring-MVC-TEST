package com.sfr.demo.web;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.sfr.demo.model.Form;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
                                   "classpath:META-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/servlet-context.xml"
})
public class HomeControllerTest {
    
    @Autowired
    private WebApplicationContext wac;
    
    private MockMvc mockMvc;
    
    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    
    @Test
    public void testInit() throws Exception {
        this.mockMvc.perform(get("/init")).andExpect(status().isOk()).andExpect(model().attributeExists("ourForm"))
        .andExpect(model().attribute("ourForm", equalTo(new Form(null, null, null))));
    }
    
    @Test
    public void testHandleSubmitFormOk() throws Exception {
        this.mockMvc.perform(post("/submitForm").param("name", "mehdi").param("age", "99").param("date", "11-07-1983"))
        .andExpect(status().isFound()).andExpect(redirectedUrl("http://www.sfr.fr"))
        .andExpect(request().attribute("age", "99"));
    }
    
    @Test
    public void testHandleSubmitFormWhenHandlingFuctionalException() throws Exception{
        this.mockMvc.perform(post("/submitForm").param("name", "mehdi").param("age", "101").param("date", "11-07-1983"))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.field", equalTo("age")));
    }
    
    @Test
    public void testHandleSubmitForInitBinderForDate() throws Exception {
        this.mockMvc.perform(post("/submitForm").param("name", "mehdi").param("age", "99").param("date", "11-17-1983"))
        .andExpect(status().isOk()).andExpect(content().contentType(new MediaType("text", "html", Charset.forName("ISO-8859-1"))))
        .andExpect(content().string(containsString("Could not parse date: Unparseable date: &quot;11-17-1983&quot;")))
        .andExpect(model().hasErrors())
        .andExpect(model().errorCount(1));
    }
    
    @Test
    public void testHandleSubmitWhenValidationError() throws Exception {
        this.mockMvc.perform(post("/submitForm").param("name", "").param("age", "coco").param("date", "11-17-1983"))
        .andExpect(status().isOk()).andExpect(content().contentType(new MediaType("text", "html", Charset.forName("ISO-8859-1"))))
        .andExpect(content().string(containsString("Could not parse date: Unparseable date: &quot;11-17-1983&quot;")))
        .andExpect(content().string(containsString("Come on man ! you need to put some name")))
        .andExpect(content().string(containsString("WTF! Age must be a set of valid digits!")))
        .andExpect(model().hasErrors())
        .andExpect(model().errorCount(3));
    }
    
}
