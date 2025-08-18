/*
 * Copyright 2014-present the original author or authors.
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

package org.springframework.restdocs.resttestclient;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;

import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.operation.OperationRequest;
import org.springframework.restdocs.operation.OperationRequestFactory;
import org.springframework.restdocs.operation.OperationRequestPart;
import org.springframework.restdocs.operation.RequestConverter;
import org.springframework.restdocs.operation.RequestCookie;
import org.springframework.test.web.servlet.client.ExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;

/**
 * A {@link RequestConverter} for creating an {@link OperationRequest} derived from an
 * {@link ExchangeResult}.
 *
 * @author Andy Wilkinson
 */
class RestTestClientRequestConverter implements RequestConverter<ExchangeResult> {

	@Override
	public OperationRequest convert(ExchangeResult result) {
		HttpHeaders headers = extractRequestHeaders(result);
		return new OperationRequestFactory().create(result.getUrl(), result.getMethod(), result.getBody(byte[].class),
				headers, extractRequestParts(result), extractCookies(headers));
	}

	private HttpHeaders extractRequestHeaders(ExchangeResult result) {
		HttpHeaders extracted = new HttpHeaders();
		extracted.putAll(result.getRequestHeaders());
		extracted.remove(RestTestClient.RESTTESTCLIENT_REQUEST_ID);
		return extracted;
	}

	private @Nullable List<OperationRequestPart> extractRequestParts(ExchangeResult result) {
		return null;
		// HttpMessageReader<Part> partHttpMessageReader = new
		// DefaultPartHttpMessageReader();
		// return new MultipartHttpMessageReader(partHttpMessageReader)
		// .readMono(ResolvableType.forClass(Part.class), new
		// ExchangeResultReactiveHttpInputMessage(result),
		// Collections.emptyMap())
		// .onErrorReturn(new LinkedMultiValueMap<>())
		// .map((partsMap) -> partsMap.values()
		// .stream()
		// .flatMap((parts) -> parts.stream().map(this::createOperationRequestPart))
		// .collect(Collectors.toList()))
		// .block();
	}

	// private OperationRequestPart createOperationRequestPart(Part part) {
	// ByteArrayOutputStream content = readPartBodyContent(part);
	// return new OperationRequestPartFactory().create(part.name(),
	// (part instanceof FilePart) ? ((FilePart) part).filename() : null,
	// content.toByteArray(),
	// part.headers());
	// }
	//
	// private ByteArrayOutputStream readPartBodyContent(Part part) {
	// ByteArrayOutputStream contentStream = new ByteArrayOutputStream();
	// DataBufferUtils.write(part.content(), contentStream).blockFirst();
	// return contentStream;
	// }

	private Collection<RequestCookie> extractCookies(HttpHeaders headers) {
		List<String> cookieHeaders = headers.get(HttpHeaders.COOKIE);
		if (cookieHeaders == null) {
			return Collections.emptyList();
		}
		headers.remove(HttpHeaders.COOKIE);
		return cookieHeaders.stream().map(this::createRequestCookie).collect(Collectors.toList());
	}

	private RequestCookie createRequestCookie(String header) {
		String[] components = header.split("=");
		return new RequestCookie(components[0], components[1]);
	}

	// private final class ExchangeResultReactiveHttpInputMessage implements
	// ReactiveHttpInputMessage {
	//
	// private final ExchangeResult result;
	//
	// private ExchangeResultReactiveHttpInputMessage(ExchangeResult result) {
	// this.result = result;
	// }
	//
	// @Override
	// public HttpHeaders getHeaders() {
	// return this.result.getRequestHeaders();
	// }
	//
	// @Override
	// public Flux<DataBuffer> getBody() {
	// byte[] requestBodyContent = this.result.getBody(byte[].class);
	// if (requestBodyContent == null) {
	// requestBodyContent = new byte[0];
	// }
	// DefaultDataBuffer buffer = new
	// DefaultDataBufferFactory().allocateBuffer(requestBodyContent.length);
	// buffer.write(requestBodyContent);
	// return Flux.fromArray(new DataBuffer[] { buffer });
	// }
	//
	// }

}
