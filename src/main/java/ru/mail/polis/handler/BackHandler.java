package ru.mail.polis.handler;

import one.nio.http.*;
import one.nio.http.Path;
import ru.mail.polis.storageManager.BasicStorage;

import java.io.IOException;
import java.util.Set;

/**
 * Created by iters on 11/16/17.
 */
public class BackHandler {

    private BasicStorage storage;

    public BackHandler(BasicStorage storage) {
        this.storage = storage;
    }

    @Path("/v0/status")
    public Response statusHandler() {
        return Response.ok("service is ready");
    }

    @Path("/v0/entity")
    public void handler(Request request,
                        HttpSession session,
                        @Param(value = "id") String id) throws IOException {

        if (id == null || id.length() == 0) {
            session.sendError(Response.BAD_REQUEST, "incorrect request");
            return;
        }

        switch (request.getMethod()) {
            case Request.METHOD_GET:
                getController(session, id);
                break;
            case Request.METHOD_PUT:
                putController(request, session, id);
                break;
            case Request.METHOD_DELETE:
                deleteController(session, id);
                break;
            default:
                session.sendError(Response.BAD_REQUEST,
                        "please, use GET, PUT or DELETE methods of HTTP");
        }
    }

    private void getController(HttpSession session, String id) throws IOException {
        if (!storage.isDataExist(id)) {
            session.sendResponse(new Response(
                    Response.NOT_FOUND, "file is not found".getBytes()));
            return;
        }

        byte[] result = storage.getData(id);
        session.sendResponse(new Response(Response.OK, result));
    }

    private void putController(Request request, HttpSession session, String id)
            throws IOException {

        byte[] body = request.getBody();
        boolean isPutted = storage.saveData(id, body);

        if (!isPutted) {
            // TODO: should be logging or send error code
        }

        session.sendResponse(new Response(
                Response.CREATED, "file was successfully created".getBytes()));
    }

    private void deleteController(HttpSession session, String id) throws IOException {
        storage.removeData(id);
        session.sendResponse(new Response(
                Response.ACCEPTED, "file was successfully deleted".getBytes()));
    }
}
