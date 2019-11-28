package com.drzewo97.ballotbox.core.service.converter;

public interface Converter<FROM, TO> {
	TO convert(FROM from);
}
