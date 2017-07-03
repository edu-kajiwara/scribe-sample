package jp.ac.tama.controller;

import com.github.scribejava.apis.GitHubApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.httpclient.okhttp.OkHttpHttpClientConfig;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * Created by kajiwarayutaka on 2017/07/04.
 */
@Controller
public class GithubAuthController  {
    @Value("${github.clientId}")
    private String githubClientId;

    @Value("${github.clientSecret}")
    private String githubClientSecret;

    @Value("${client.url}")
    private String clientUrl;


    @RequestMapping(value = "/sign_in" ,method = RequestMethod.GET)
    public String signIn(@RequestParam(value = "state", required = false) String state){
        try (OAuth20Service service = new ServiceBuilder()
                .apiKey(githubClientId)
                .apiSecret(githubClientSecret)
                .state(state)
                .callback(clientUrl+"/scribe_front/")
                .httpClientConfig(OkHttpHttpClientConfig.defaultConfig())
                .build(GitHubApi.instance())){
            val redirectUrl = service.getAuthorizationUrl();
            return "redirect:" + redirectUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:" + clientUrl;
    }

}
