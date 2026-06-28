package com.crm.read.api;

import com.crm.read.store.RedisViewStore;
import com.crm.read.view.ContactView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = ContactQueryController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class ContactQueryControllerIT {
    @Autowired MockMvc mvc;
    @MockBean RedisViewStore store;

    @Test
    void getContact_found_returns200() throws Exception {
        when(store.findContact("t1", "id-1"))
            .thenReturn(Optional.of(new ContactView("id-1", "t1", "Alice", "alice@co.com")));

        mvc.perform(get("/api/contacts/t1/id-1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    void getContact_notFound_returns404() throws Exception {
        when(store.findContact("t1", "id-999")).thenReturn(Optional.empty());
        mvc.perform(get("/api/contacts/t1/id-999")).andExpect(status().isNotFound());
    }
}
