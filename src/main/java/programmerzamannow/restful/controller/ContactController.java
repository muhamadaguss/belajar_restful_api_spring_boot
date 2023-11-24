package programmerzamannow.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import programmerzamannow.restful.entity.User;
import programmerzamannow.restful.model.*;
import programmerzamannow.restful.service.ContactService;

import java.util.List;

@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping(path = "/api/contacts",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ContactResponse> createContact(User user, @RequestBody CreateContactRequest request){
        ContactResponse contactResponse = contactService.createContact(user, request);

        return WebResponse.<ContactResponse>builder().data(contactResponse).build();
    }

    @GetMapping(path = "/api/contacts/{contactId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ContactResponse> getContact(User user,@PathVariable("contactId") String id){
        ContactResponse contactResponse = contactService.getContact(user, id);

        return WebResponse.<ContactResponse>builder().data(contactResponse).build();
    }

    @PutMapping(path = "/api/contacts/{contactId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<ContactResponse> updateContact(User user,
                                                      @RequestBody UpdateContactRequest request,
                                                      @PathVariable("contactId") String id){
        request.setId(id);
        ContactResponse contactResponse = contactService.updateContact(user, request);

        return WebResponse.<ContactResponse>builder().data(contactResponse).build();
    }

    @DeleteMapping(path = "/api/contacts/{contactId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> removeContact(User user,@PathVariable("contactId") String id){

        contactService.removeContact(user,id);

        return WebResponse.<String>builder().data("Ok").build();
    }

    @GetMapping(path = "/api/contacts",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<ContactResponse>> searchContact(User user,
                                                            @RequestParam(value = "name",
                                                                    required = false) String name,
                                                            @RequestParam(value = "email",
                                                                    required = false) String email,
                                                            @RequestParam(value = "phone",
                                                                    required = false) String phone,
                                                            @RequestParam(value = "page",
                                                                    required = false,
                                                                    defaultValue = "0") Integer page,
                                                            @RequestParam(value = "size",
                                                                    required = false,
                                                                    defaultValue = "10") Integer size){
        SearchContactRequest request = SearchContactRequest.builder()
                                        .name(name)
                                        .email(email)
                                        .phone(phone)
                                        .page(page)
                                        .size(size)
                                        .build();

        Page<ContactResponse> contactResponses = contactService.searchContact(user, request);
        return WebResponse.<List<ContactResponse>>builder()
                .data(contactResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(contactResponses.getNumber())
                        .totalPage(contactResponses.getTotalPages())
                        .size(contactResponses.getSize())
                        .build())
                .build();
    }
}
