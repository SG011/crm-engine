package com.crm.write.api;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.crm.write.handler.ContactCommandHandler;
import com.crm.write.handler.DealCommandHandler;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ContactCommandController.class, DealCommandController.class},
            excludeAutoConfiguration = SecurityAutoConfiguration.class)
class ContactCommandControllerIT {
    @Autowired MockMvc mvc;
    @MockBean ContactCommandHandler contactHandler;
    @MockBean DealCommandHandler dealHandler;

    @Test
    void createContact_returns202() throws Exception {
        mvc.perform(post("/api/contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"idempotencyKey":"key-1","tenantId":"t1","name":"Alice","email":"alice@co.com"}
            """))
            .andExpect(status().isAccepted());
    }

    @Test
    void updateDealStage_returns202() throws Exception {
        mvc.perform(patch("/api/deals/deal-1/stage")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"idempotencyKey":"key-2","tenantId":"t1","dealId":"deal-1","newStage":"PROPOSAL"}
            """))
            .andExpect(status().isAccepted());
    }
}
