package dgcplg.moviebooking.exception;

import dgcplg.moviebooking.model.Error.CodeEnum;
import org.springframework.dao.*;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.LobRetrievalFailureException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static dgcplg.moviebooking.model.Error.CodeEnum.*;

@Component
public class ExceptionToCodeEnumConverter {
    private final Map<Class<? extends Throwable>, CodeEnum> exc2Code;
    private final CodeEnum defaultValue = CodeEnum.SYSTEMERROR;

    {
        exc2Code = new HashMap<>();
        exc2Code.put(ObjectRetrievalFailureException.class, OBJECTNOTFOUND);
        exc2Code.put(LobRetrievalFailureException.class, OBJECTNOTFOUND);
        exc2Code.put(IncorrectResultSetColumnCountException.class, INVALIDSTATE);
        exc2Code.put(IncorrectResultSizeDataAccessException.class, INVALIDSTATE);
        exc2Code.put(TypeMismatchDataAccessException.class, TYPEMISMATCH);
        exc2Code.put(InvalidResultSetAccessException.class, INVALIDARGUMENT);
        exc2Code.put(InvalidDataAccessResourceUsageException.class, INVALIDARGUMENT);
        exc2Code.put(PermissionDeniedDataAccessException.class, INSUFFICIENTPERMISSIONS);
        exc2Code.put(DataIntegrityViolationException.class, DATAINTEGRITYVIOLATIONEXCEPTION);
        exc2Code.put(IncorrectUpdateSemanticsDataAccessException.class, OBJECTCANNOTBESAVED);
        exc2Code.put(DuplicateKeyException.class, UNIQUENESSVIOLATIONERROR);
    }

    public CodeEnum getCodeFor(Throwable throwable) {
        return exc2Code.getOrDefault(throwable.getClass(), defaultValue);
    }
}
