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
package com.fizzed.blaze.groovy;

import com.fizzed.blaze.Context;
import com.fizzed.blaze.core.BlazeException;
import com.fizzed.blaze.core.AbstractEngine;
import groovy.lang.Binding;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import java.io.IOException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlazeGroovyEngine extends AbstractEngine<BlazeGroovyScript> {
    static final private Logger log = LoggerFactory.getLogger(AbstractEngine.class);
    
    private GroovyScriptEngine groovy;

    @Override
    public String getName() {
        return "groovy";
    }
    
    @Override
    public String getFileExtension() {
        return ".groovy";
    }
    
    @Override
    public void init(Context initialContext) throws BlazeException {
        super.init(initialContext);
        
        try {
            // initialize engine with the base directory
            URL root = initialContext.baseDir().toUri().toURL();
            this.groovy = new GroovyScriptEngine(new URL[] { root });
        } catch (IOException e) {
            throw new BlazeException("Unable to create groovy", e);
        }
    }

    @Override
    public BlazeGroovyScript compile(Context context) throws BlazeException {
        try {
            // must be valid url...
            String path = context.scriptFile().toUri().toURL().toString();
            
            Binding binding = new Binding();
            
            // add small number of useful bindings
            // not sure its best practice to polute namespace at all
            //binding.setVariable("context", context);
            //binding.setVariable("log", context.logger());
            //binding.setVariable("config", context.config());
 
            Script script = script = this.groovy.createScript(path, binding);
            
            script.run();
            
            return new BlazeGroovyScript(this, script);
        } catch (ResourceException | IOException | ScriptException e) {
            throw new BlazeException("Unable to compile groovy script", e);
        }
    }
}
