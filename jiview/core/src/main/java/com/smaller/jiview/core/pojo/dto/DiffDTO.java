package com.smaller.jiview.core.pojo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by YANGL on 2018/6/14.
 */
@Getter
@Setter
public class DiffDTO<T>{
    private List<T> added;
    private List<T> deleted;
}
