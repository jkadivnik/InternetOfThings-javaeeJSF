package be.kadivnik.iot.service;

import java.io.Serializable;

public interface DataAccessService<T extends Serializable> {
	T create(T t);
	T update(T t);
	void delete(T t);
}
