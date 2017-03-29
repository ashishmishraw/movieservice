package in.geektrust.movieservice.service;

import in.geektrust.movieservice.exception.InvalidInputException;

import javax.ws.rs.core.Response;

/**
 * Base class for REST service
 * Created by mishra.ashish@icloud.com
 */
public class ServiceBase {


    /**
     * Error response
     *
     * @return {@link Response} response
     */
    public Response generateErrorResponse(final Throwable ex, Response.Status ERR_CODE, String errorMessage) {

        if (ex instanceof InvalidInputException) {
            ERR_CODE = Response.Status.BAD_REQUEST;
            errorMessage = ex.getMessage();
        }

        final Response.ResponseBuilder b =
                Response.status(ERR_CODE).entity(errorMessage);
        //logger.error ( ex.getStackTrace() );
        return b.build();
    }
}
