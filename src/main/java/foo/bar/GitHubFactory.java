package foo.bar;

import java.io.IOException;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

/**
 * Created by hbraun on 20.06.18.
 */
public class GitHubFactory {

    public GHRepository createRepo(String token, String name) {
        try {

            // 88afdd009b19ec3bc166101944a8251183cbcc1b
            GitHub github = GitHub.connect("", token);
            GHRepository repository = github.getRepository(name);
            return repository;
        } catch (IOException e) {
            throw new RuntimeException("Failed to createRepo repo", e);
        }
    }
}
