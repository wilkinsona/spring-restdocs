/*
 * Copyright 2014-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.restdocs.templates;

/**
 * Thrown when template rendering fails.
 *
 * @author Andy Wilkinson
 * @since 2.0.5
 * @see Template#render(java.util.Map)
 */
public class TemplateRenderingException extends RuntimeException {

	/**
	 * Creates a new {@code TemplateRenderingException} with the given {@code message} and
	 * {@code cause}.
	 * @param message the failure message
	 * @param cause the cause of the failure
	 */
	public TemplateRenderingException(String message, Throwable cause) {
		super(message, cause);
	}

}
