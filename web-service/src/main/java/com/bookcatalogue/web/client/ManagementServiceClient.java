package com.bookcatalogue.web.client;

import com.bookcatalogue.web.model.Book;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
public class ManagementServiceClient {

    private final Client client;
    private final String managementServiceUrl = "http://localhost:8081/api/books";

    public ManagementServiceClient() {
        ClientConfig config = new ClientConfig();
        config.register(JacksonFeature.class);
        this.client = ClientBuilder.newClient(config);
    }

    public List<Book> getAllBooks() {
        return client.target(managementServiceUrl)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Book>>() {});
    }

    public Book getBookById(Long id) {
        return client.target(managementServiceUrl + "/" + id)
                .request(MediaType.APPLICATION_JSON)
                .get(Book.class);
    }

    public Book createBook(Book book) {
        Response response = client.target(managementServiceUrl)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(book, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200) {
            return response.readEntity(Book.class);
        } else {
            throw new RuntimeException("Failed to create book: " + response.getStatus());
        }
    }

    public Book updateBook(Long id, Book book) {
        Response response = client.target(managementServiceUrl + "/" + id)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(book, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200) {
            return response.readEntity(Book.class);
        } else {
            throw new RuntimeException("Failed to update book: " + response.getStatus());
        }
    }

    public void deleteBook(Long id) {
        Response response = client.target(managementServiceUrl + "/" + id)
                .request(MediaType.APPLICATION_JSON)
                .delete();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed to delete book: " + response.getStatus());
        }
    }
}