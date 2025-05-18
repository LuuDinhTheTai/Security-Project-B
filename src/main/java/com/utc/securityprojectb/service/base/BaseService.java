package com.utc.securityprojectb.service.base;

import java.util.List;

public interface BaseService<T, ID> {
  
  T create(T t);
  T find(ID id);
  List<T> list();
  T update(T t);
  void delete(ID id);
}
