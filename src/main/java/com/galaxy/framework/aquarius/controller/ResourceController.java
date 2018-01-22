package com.galaxy.framework.aquarius.controller;

import com.galaxy.framework.aquarius.entity.Resource;
import com.galaxy.framework.aquarius.service.ResourceService;
import com.galaxy.framework.pisces.exception.db.NotExistException;
import com.galaxy.framework.pisces.exception.rule.EmptyException;
import com.galaxy.framework.pisces.vo.TreeVo;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/get")
    public Resource get(String code) {
        Resource resource = resourceService.selectByCode(code);
        if (resource != null) {
            return resource;
        }
        throw new NotExistException("查询的资源不存在");
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/tree")
    public List<TreeVo> tree() {
        List<Resource> resources = resourceService.selectAllOrderByFullPath();
        List<TreeVo> treeVos = Lists.newArrayList();
        resources.forEach(resource -> {
            TreeVo treeVo = new TreeVo();
            treeVo.setId(resource.getCode());
            treeVo.setText(resource.getName());
            treeVo.setType("folder");
            if (StringUtils.isEmpty(resource.getResourceCode())) {
                treeVo.setParent("#");
            } else {
                treeVo.setParent(resource.getResourceCode());
            }
            treeVo.setState(ImmutableMap.of("opened", true));
            treeVos.add(treeVo);
        });
        return treeVos;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/findAll")
    public List<Resource> findAll() {
        List<Resource> resources = resourceService.find(ImmutableMap.of("status", "启用"));
        return resources;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/save")
    public Resource save(@RequestBody Resource resource) {
        if (resource != null) {
            resource.setStatus("启用");
            resourceService.save(resource);
            return resource;
        }
        throw new EmptyException();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/delete")
    public int delete(@RequestBody String code) {
        return resourceService.deleteByCode(code);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/reuse")
    public int reuse(@RequestBody String code) {
        return resourceService.reuse(code);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/grant")
    public int grant(@RequestBody Map<String, Object> requestBody) {
        String code = (String) requestBody.get("code");
        List<String> creates = (List<String>) requestBody.get("creates");
        List<String> deletes = (List<String>) requestBody.get("deletes");
        return resourceService.grant(code, creates, deletes);
    }
}
