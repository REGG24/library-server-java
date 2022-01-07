package com.regg.library.service;

import com.regg.library.model.Return;
import com.regg.library.model.RecordNotFoundException;

import java.util.Collection;

public interface ReturnService {
    Return create(Return return_);
    Collection<Return> list(int limit);
    Return get(Long id) throws RecordNotFoundException;
    Return update(Return return_, Long id) throws RecordNotFoundException;
    Boolean delete(Long id) throws RecordNotFoundException;
}
