package programmerzamannow.restful.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import programmerzamannow.restful.entity.Address;
import programmerzamannow.restful.entity.Contact;
import programmerzamannow.restful.entity.User;
import programmerzamannow.restful.model.AddressResponse;
import programmerzamannow.restful.model.CreateAddressRequest;
import programmerzamannow.restful.model.UpdateAddressRequest;
import programmerzamannow.restful.model.WebResponse;
import programmerzamannow.restful.repository.AddressRepository;
import programmerzamannow.restful.repository.ContactRepository;
import programmerzamannow.restful.repository.UserRepository;
import programmerzamannow.restful.security.BCrypt;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AddressControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        addressRepository.deleteAll();
        contactRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("test");
        user.setPassword(BCrypt.hashpw("rahasia",BCrypt.gensalt()));
        user.setName("Test");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000L);
        userRepository.save(user);

        Contact contact = new Contact();
        contact.setId("11111");
        contact.setUser(user);
        contact.setFirstName("Muhamad");
        contact.setLastName("Agus");
        contact.setEmail("agus@example.com");
        contact.setPhone("12345678998");
        contactRepository.save(contact);
    }

    @Test
    void testCreateAddressBadRequest() throws Exception{
        CreateAddressRequest request = new CreateAddressRequest();
        request.setCountry("");

        mockMvc.perform(
                post("/api/contacts/test/addresses")
                        .header("X-API-TOKEN","test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void testCreateAddressSuccess() throws Exception{
        CreateAddressRequest request = new CreateAddressRequest();
        request.setStreet("Jalan Sesama");
        request.setCity("Jakarta Timur");
        request.setProvince("DKI Jakarta");
        request.setCountry("Indonesia");
        request.setPostalCode("17444");
        request.setContactId("11111");

        mockMvc.perform(
                post("/api/contacts/11111/addresses")
                        .header("X-API-TOKEN","test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<AddressResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals(request.getCountry(),response.getData().getCountry());
            assertEquals(request.getStreet(),response.getData().getStreet());
            assertEquals(request.getCity(),response.getData().getCity());
            assertEquals(request.getProvince(),response.getData().getProvince());
            assertEquals(request.getPostalCode(),response.getData().getPostalCode());
        });
    }

    @Test
    void testGetAddressNotFound() throws Exception{

        mockMvc.perform(
                get("/api/contacts/11111/addresses/11111")
                        .header("X-API-TOKEN","test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void testGetAddressSuccess() throws Exception{
        Contact contact = contactRepository.findById("11111").orElseThrow();
        Address address = new Address();
        address.setId(UUID.randomUUID().toString());
        address.setContact(contact);
        address.setStreet("Jalan Sesama");
        address.setCity("Jakarta Timur");
        address.setProvince("DKI Jakarta");
        address.setCountry("Indonesia");
        address.setPostalCode("17444");
        addressRepository.save(address);

        mockMvc.perform(
                get("/api/contacts/11111/addresses/"+address.getId())
                        .header("X-API-TOKEN","test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<AddressResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals(address.getId(),response.getData().getId());
            assertEquals(address.getStreet(),response.getData().getStreet());
            assertEquals(address.getCity(),response.getData().getCity());
            assertEquals(address.getProvince(),response.getData().getProvince());
            assertEquals(address.getCountry(),response.getData().getCountry());
        });
    }

    @Test
    void testUpdateAddressBadRequest() throws Exception{
        UpdateAddressRequest request = new UpdateAddressRequest();
        request.setCountry("");

        mockMvc.perform(
                put("/api/contacts/11111/addresses/11111")
                        .header("X-API-TOKEN","test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void testUpdateAddressSuccess() throws Exception{
        Contact contact = contactRepository.findById("11111").orElseThrow();
        Address address = new Address();
        address.setId(UUID.randomUUID().toString());
        address.setContact(contact);
        address.setStreet("Jalan Sesama");
        address.setCity("Jakarta Timur");
        address.setProvince("DKI Jakarta");
        address.setCountry("Indonesia");
        address.setPostalCode("17444");
        addressRepository.save(address);

        UpdateAddressRequest request = new UpdateAddressRequest();
        request.setId(address.getId());
        request.setContactId(contact.getId());
        request.setStreet("Jalan Sesame");
        request.setCity("Bekasi");
        request.setProvince("Jawa Barat");
        request.setCountry("Indonesia");
        request.setPostalCode("17411");

        mockMvc.perform(
                put("/api/contacts/11111/addresses/"+address.getId())
                        .header("X-API-TOKEN","test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<AddressResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals(request.getId(),response.getData().getId());
            assertEquals(request.getStreet(),response.getData().getStreet());
            assertEquals(request.getCity(),response.getData().getCity());
            assertEquals(request.getProvince(),response.getData().getProvince());
            assertEquals(request.getCountry(),response.getData().getCountry());
        });
    }

    @Test
    void testRemoveAddressNotFound() throws Exception{

        mockMvc.perform(
                delete("/api/contacts/11111/addresses/11111")
                        .header("X-API-TOKEN","test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void testRemoveAddressSuccess() throws Exception{
        Contact contact = contactRepository.findById("11111").orElseThrow();
        Address address = new Address();
        address.setId(UUID.randomUUID().toString());
        address.setContact(contact);
        address.setStreet("Jalan Sesama");
        address.setCity("Jakarta Timur");
        address.setProvince("DKI Jakarta");
        address.setCountry("Indonesia");
        address.setPostalCode("17444");
        addressRepository.save(address);

        mockMvc.perform(
                delete("/api/contacts/11111/addresses/"+address.getId())
                        .header("X-API-TOKEN","test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals("Ok",response.getData());
            assertFalse(addressRepository.existsById(address.getId()));
        });
    }

    @Test
    void testGetListAddressNotFound() throws Exception{

        mockMvc.perform(
                get("/api/contacts/salah/addresses")
                        .header("X-API-TOKEN","test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void testGetListAddressSuccess() throws Exception{
        Contact contact = contactRepository.findById("11111").orElseThrow();

        for (int i = 0; i < 10; i++) {
            Address address = new Address();
            address.setId(UUID.randomUUID().toString());
            address.setContact(contact);
            address.setStreet("Jalan Sesama"+i);
            address.setCity("Jakarta Timur");
            address.setProvince("DKI Jakarta");
            address.setCountry("Indonesia");
            address.setPostalCode("17444");
            addressRepository.save(address);
        }

        mockMvc.perform(
                get("/api/contacts/11111/addresses")
                        .header("X-API-TOKEN","test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<AddressResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals(10,response.getData().size());
        });
    }

}