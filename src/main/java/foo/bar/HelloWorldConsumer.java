/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package foo.bar;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.ScheduledPollConsumer;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHRepository;

/**
 * The HelloWorld consumer.
 */
public class HelloWorldConsumer extends ScheduledPollConsumer {
    private final HelloWorldEndpoint endpoint;

    public HelloWorldConsumer(HelloWorldEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
        this.endpoint = endpoint;
    }

    @Override
    protected int poll() throws Exception {
        Exchange exchange = endpoint.createExchange();

        String repo = (String) endpoint.getEndpointConfiguration().getParameter("repository");
        String token = (String) endpoint.getEndpointConfiguration().getParameter("token");

        GHRepository repository = new GitHubFactory().createRepo(token, repo);

        List<GHIssue> issues = repository.getIssues(GHIssueState.ALL);
        exchange.getIn().setBody(issues);
        return issues.size();

    }
}
