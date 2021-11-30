package com.diosa.model.permission;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
