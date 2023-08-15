package dgcplg.moviebooking.exception;

import dgcplg.moviebooking.model.Error.CodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static dgcplg.moviebooking.model.Error.CodeEnum.*;
import static org.springframework.http.HttpStatus.*;

@Component
public class CodeEnumToHttpStatusConverter {
    private final Map<CodeEnum, HttpStatus> code2http;
    private final HttpStatus defaultValue = INTERNAL_SERVER_ERROR;

    {
        code2http = new HashMap<>();
        code2http.put(NOTNULL, BAD_REQUEST);
        code2http.put(INVALIDARGUMENT, BAD_REQUEST);
        code2http.put(TYPEMISMATCH, BAD_REQUEST);
        code2http.put(MISSINGID, BAD_REQUEST);
        code2http.put(INVALIDFILEFORMAT, BAD_REQUEST);
        code2http.put(INVALIDERROR, BAD_REQUEST);
        code2http.put(INSUFFICIENTPERMISSIONS, FORBIDDEN);
        code2http.put(OBJECTNOTFOUND, NOT_FOUND);
    }

    public HttpStatus getHttpFor(CodeEnum code) {
        return code2http.getOrDefault(code, defaultValue);
    }
}
