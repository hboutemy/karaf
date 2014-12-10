/*
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
package org.apache.karaf.profile.command;

import org.apache.karaf.profile.Profile;
import org.apache.karaf.profile.ProfileBuilder;
import org.apache.karaf.profile.ProfileService;
import org.apache.karaf.profile.command.completers.ProfileCompleter;
import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.Completion;
import org.apache.karaf.shell.api.action.Option;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;

@Command(name = "copy", scope = "profile", description = "Copies the specified source profile")
@Service
public class ProfileCopy implements Action {

    @Option(name = "-f", aliases = "--force", description = "Flag to allow overwriting the target profile (if exists).")
    private boolean force;

    @Argument(index = 0, required = true, name = "source profile", description = "Name of the source profile.")
    @Completion(ProfileCompleter.class)
    private String source;

    @Argument(index = 1, required = true, name = "target profile", description = "Name of the target profile.")
    private String target;

    @Reference
    private ProfileService profileService;

    @Override
    public Object execute() throws Exception {
        Profile profile = ProfileBuilder.Factory.createFrom(profileService.getProfile(source))
                .identity(target)
                .getProfile();
        profileService.createProfile(profile);
        return null;
    }

}
