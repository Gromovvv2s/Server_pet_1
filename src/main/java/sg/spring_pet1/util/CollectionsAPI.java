package sg.spring_pet1.util;

import lombok.experimental.UtilityClass;

@UtilityClass
/**
 * Видимо, или Spring или DispetcherServlet самостоятельно подставит версию API [v1] из application.properties
 * эти константы идентичны request.getRequestURI()  --!!--используется в request-фильтрах--!!--
 */
// в контроллерах: api, в фильтрах: [v1] + api
public class CollectionsAPI {
    //мб просто передавать 2 строки
    public static String getUrlWithVersion(String url) {
        return V1 + url;
    }
    /**
     * spring.application.api-version = [ /v1 ]
     */
    public static final String V1 = "/v1";
    /**
     * RequestMapping = [ /login ]
     */
    public static final String LOG_IN = "/login";
    /**
     * RequestMapping = [ /person/friends ]
     */
    public static final String PERSON_FRIENDS = "/person/friends";

    /**тестовый
     *
     */
    public static final String INDEX = "/index";
    /**тестовый
     *
     */
    public static final String TEST = "/test";
}
