package io.wolfbeacon.server.controller.api.v1;

import io.wolfbeacon.server.model.Email;
import io.wolfbeacon.server.service.MailingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Aaron on 22/05/2016.
 */
@RestController
@RequestMapping("/api/v1/mail")
public class MailingListController {
    @Autowired
    MailingListService mailingListService;

    @RequestMapping(value = "/", method = POST)
    public void addEmail(@RequestBody Email email) {
        mailingListService.addEmail(email);
    }
}
