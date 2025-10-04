package sg.spring_pet1.util;

import lombok.experimental.UtilityClass;

@UtilityClass
/**
 * Видимо, или Spring или DispetcherServlet самостоятельно подставит версию API [v1] из application.properties
 * эти константы идентичны request.getRequestURI()  --!!--используется в request-фильтрах--!!--
 */
public class CollectionsAPI {
    // можно в константу добавить еще версию апи
    // и формировать другие апи используя эту константу
    // в контроллерах: api, в фильтрах: [v1] + api
    public static final String API_LOG_IN = "/v1/login";
}
