package hello.config;

import hello.Application;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.annotate.JacksonStdImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.net.MulticastSocket;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by yangyu on 4/10/17.
 */

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = Application.class)
@ActiveProfiles("mvc")
public class AuthServerOAuth2ConfigTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private static final String CLIENT_ID = "fooClientIdPassword";
    private static final String CLIENT_SECRET = "secret";
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    private static final String AUTH_SERVER_URI = "/oauth/token";

    private MockMvc mockMvc;

    private String obtainAccessToken(String username, String password) throws Exception {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("grant_type", "password");
        params.add("client_id", CLIENT_ID);
        params.add("username", username);
        params.add("password", password);

        // @formatter:off

        ResultActions result = mockMvc.perform(post(AUTH_SERVER_URI)
                .params(params)
                .with(httpBasic(CLIENT_ID, CLIENT_SECRET))
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE));

        // @formatter:on

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void testOauth() throws Exception {
        String accessToken = obtainAccessToken("tom", "123456");

        ResultActions result = mockMvc.perform(get("/api/profiles")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(CONTENT_TYPE)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE));

        String resultString = result.andReturn().getResponse().getContentAsString();
        System.out.println(resultString);
    }


    private String getProfiles(String token) throws Exception {
        ResultActions result = mockMvc.perform(get("/api/profiles")
                .header("Authorization", "Bearer "+token)
                .contentType(CONTENT_TYPE).accept(CONTENT_TYPE))
                .andExpect(status().isOk());
        String resultString = result.andReturn().getResponse().getContentAsString();
        return resultString;
    }

    private String createProfiles(String token, String profileName) throws Exception {
        ResultActions result = mockMvc.perform(get("/api/profile_add")
                .param("pname", profileName)
                .header("Authorization", "Bearer "+token)
                .contentType(CONTENT_TYPE)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk());
        String resultString = result.andReturn().getResponse().getContentAsString();
        return resultString;
    }

    private String deleteOneProfile(String token, String pname) throws Exception {
        ResultActions result = mockMvc.perform(post("/api/profile_delete")
                .param("pname", pname)
                .header("Authorization", "Bearer " + token)
                .contentType(CONTENT_TYPE)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk());
        String rt = result.andReturn().getResponse().getContentAsString();
        return rt;
    }

    private String addOneProfileStock(String token, String pname, String stockName, float price, int share, Date date) throws Exception {
        ResultActions result = mockMvc.perform(get("/api/profile_symbol_add")
                .param("pname", pname)
                .param("sname", stockName)
                .param("price", Float.toString(price))
                .param("share", Integer.toString(share))
                .param("bought_date", date.toString())
                .header("Authorization", "Bearer " + token)
                .contentType(CONTENT_TYPE)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk());
        String rt = result.andReturn().getResponse().getContentAsString();
        return rt;
    }

    private String getAllProfileStocks(String token, String pname) throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/api/profile_symbols")
                .param("pname", pname)
                .header("Authorization", "Bearer " + token)
                .contentType(CONTENT_TYPE)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk());
        String rt = resultActions.andReturn().getResponse().getContentAsString();
        return rt;
    }

    private String deleteProfileStock(String token, String pname, Integer sid) throws Exception {
        ResultActions resultActions = mockMvc.perform(post("/api/profile_symbol_delete")
                .param("pname", pname)
                .param("profile_stock_id", Integer.toString(sid))
                .header("Authorization", "Bearer " + token)
                .contentType(CONTENT_TYPE)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk());
        String rt = resultActions.andReturn().getResponse().getContentAsString();
        return rt;
    }

    @Test
    public void testFlow() throws Exception {
        String accessToken = obtainAccessToken("tom", "123456");

        //test profiles
        String profiles = getProfiles(accessToken);
        System.out.println(profiles);

        String addProfile = createProfiles(accessToken, "technology");
        System.out.println(addProfile);

        profiles = getProfiles(accessToken);
        System.out.println(profiles);

        //add one profile stock
        String addOneStock = addOneProfileStock(accessToken, "technology", "AAPL", 150, 100, new Date());
        System.out.println(addOneStock);

        //and another profile stock
        String addAnotherStock = addOneProfileStock(accessToken, "technology", "AAPL",
                150, 200, new Date());
        System.out.println(addAnotherStock);

        //get all profile stocks
        String allProfileStocks = getAllProfileStocks(accessToken, "technology");
        System.out.println(allProfileStocks);

        //delete one profile stock
        JacksonJsonParser parser = new JacksonJsonParser();
        List<Object> objs = parser.parseList(allProfileStocks);
        System.out.println(objs);
        Map<String, Object> firstStock = (Map<String, Object>)objs.get(0);
        int sid = (Integer) firstStock.get("sid");
        String deleteStock = deleteProfileStock(accessToken, "technology", sid);
        System.out.println(deleteStock);
        allProfileStocks = getAllProfileStocks(accessToken, "technology");
        System.out.println(allProfileStocks);

        //delete another stock
        Map<String, Object> secondStock = (Map<String, Object>)objs.get(1);
        sid = (Integer)secondStock.get("sid");
        deleteStock = deleteProfileStock(accessToken, "technology", sid);
        System.out.println(deleteStock);


        //delete the profile
        String deleteProfile = deleteOneProfile(accessToken, "technology");
        System.out.println(deleteProfile);

        profiles = getProfiles(accessToken);
        System.out.println(profiles);


    }

}