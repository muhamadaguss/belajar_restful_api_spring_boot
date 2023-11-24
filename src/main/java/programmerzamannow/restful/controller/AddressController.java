package programmerzamannow.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import programmerzamannow.restful.entity.User;
import programmerzamannow.restful.model.AddressResponse;
import programmerzamannow.restful.model.CreateAddressRequest;
import programmerzamannow.restful.model.UpdateAddressRequest;
import programmerzamannow.restful.model.WebResponse;
import programmerzamannow.restful.service.AddressService;

import java.util.List;

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping(path = "/api/contacts/{contactId}/addresses",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<AddressResponse> createAddress(User user,
                                               @RequestBody CreateAddressRequest request,
                                               @PathVariable("contactId") String contactId){
        request.setContactId(contactId);

        AddressResponse addressResponse = addressService.createAddress(user, request);

        return WebResponse.<AddressResponse>builder().data(addressResponse).build();
    }

    @GetMapping(path = "/api/contacts/{contactId}/addresses/{addressId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<AddressResponse> getAddress(User user,
                                                   @PathVariable String contactId,
                                                   @PathVariable String addressId){
        AddressResponse addressResponse = addressService.getAddress(user, contactId, addressId);

        return WebResponse.<AddressResponse>builder().data(addressResponse).build();
    }

    @PutMapping(path = "/api/contacts/{contactId}/addresses/{addressId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<AddressResponse> UpdateAddress(User user,
                                                      @RequestBody UpdateAddressRequest request,
                                                      @PathVariable("contactId") String contactId,
                                                      @PathVariable("addressId") String addressId){
        request.setContactId(contactId);
        request.setId(addressId);

        AddressResponse addressResponse = addressService.updateAddress(user, request);

        return WebResponse.<AddressResponse>builder().data(addressResponse).build();
    }

    @DeleteMapping(path = "/api/contacts/{contactId}/addresses/{addressId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> removeAddress(User user,
                                             @PathVariable("contactId") String contactId,
                                             @PathVariable("addressId") String addressId){

        addressService.removeAddress(user,contactId,addressId);

        return WebResponse.<String>builder().data("Ok").build();
    }

    @GetMapping(path = "/api/contacts/{contactId}/addresses",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<AddressResponse>> getAddress(User user,
                                                   @PathVariable String contactId){
        List<AddressResponse> addressResponses = addressService.listAddress(user, contactId);

        return WebResponse.<List<AddressResponse>>builder().data(addressResponses).build();
    }
}
