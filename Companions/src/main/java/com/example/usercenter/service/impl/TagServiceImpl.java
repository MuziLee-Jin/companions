package com.example.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.usercenter.model.domain.Tag;
import com.example.usercenter.service.TagService;
import com.example.usercenter.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
* @author huanguang
* @description 针对表【tag(用户标签)】的数据库操作Service实现
* @createDate 2024-04-23 20:04:36
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

}




