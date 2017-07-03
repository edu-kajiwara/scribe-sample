package jp.ac.tama.controller;

import com.github.scribejava.apis.GitHubApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.httpclient.okhttp.OkHttpHttpClientConfig;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by kajiwarayutaka on 2017/07/04.
 */
@RestController
public class GithubTokenController  {
    @Value("${github.clientId}")
    private String githubClientId;

    @Value("${github.clientSecret}")
    private String githubClientSecret;

    @Value("${client.url}")
    private String clientUrl;

    private static final String PROTECTED_RESOURCE_URL = "https://api.github.com/user";



    @RequestMapping(value ="/token",method = RequestMethod.POST)
    public AccessToken callback(@RequestBody TokenRequest tokenRequest
    ) throws ExecutionException, InterruptedException {
        try (OAuth20Service service = new ServiceBuilder()
                .apiKey(githubClientId)
                .apiSecret(githubClientSecret)
                .state(tokenRequest.getState())
                .callback(clientUrl+"/scribe_front/")
                .httpClientConfig(OkHttpHttpClientConfig.defaultConfig())
                .build(GitHubApi.instance())){
            val accessToken = service.getAccessToken(tokenRequest.getCode());
            val returnAccessToken = new AccessToken();
            returnAccessToken.setAccessToken(accessToken.getAccessToken());
            returnAccessToken.setExpiresIn(accessToken.getExpiresIn());
            returnAccessToken.setRefreshToken(accessToken.getRefreshToken());
            returnAccessToken.setScope(accessToken.getScope());
            returnAccessToken.setTokenType(accessToken.getTokenType());
            returnAccessToken.setRawResponse(accessToken.getRawResponse());
            return returnAccessToken;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    @RequestMapping(value ="/isLogin", method = RequestMethod.POST  )
    public GithubResponse CheckLogin(@RequestBody AccessToken accessToken){
        try (OAuth20Service service = new ServiceBuilder()
                .apiKey(githubClientId)
                .apiSecret(githubClientSecret)
                .httpClientConfig(OkHttpHttpClientConfig.defaultConfig())
                .build(GitHubApi.instance())){
            val githubRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
            val oAuth2AccessToken = new OAuth2AccessToken(accessToken.getAccessToken(),accessToken.getTokenType(),accessToken.getExpiresIn(),
                    accessToken.getRefreshToken(),accessToken.getScope(),accessToken.getRawResponse());
            service.signRequest(oAuth2AccessToken,githubRequest);
            val githubResponse = service.execute(githubRequest);
            val returnGithubResponse = new GithubResponse(githubResponse.getCode(),githubResponse.getBody(),githubResponse.getMessage());
            return returnGithubResponse;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;

    }


}
