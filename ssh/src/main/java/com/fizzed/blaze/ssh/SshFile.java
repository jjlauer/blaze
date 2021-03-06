/*
 * Copyright 2015 Fizzed, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fizzed.blaze.ssh;

import java.nio.file.Path;

/**
 *
 * @author joelauer
 */
public class SshFile {
    
    private final Path path;
    private final SshFileAttributes attributes;

    public SshFile(Path path, SshFileAttributes attributes) {
        this.path = path;
        this.attributes = attributes;
    }
    
    public Path path() {
        return path;
    }
    
    public String fileName() {
        return path.getFileName().toString();
    }

    public SshFileAttributes attributes() {
        return attributes;
    }
    
}
