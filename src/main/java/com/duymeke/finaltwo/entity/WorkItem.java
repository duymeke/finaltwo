package com.duymeke.finaltwo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class WorkItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String text;
    private boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Account account;

    @ManyToMany
    @JoinTable(
            name = "workItem_labels",
            joinColumns = @JoinColumn(name = "workItem_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private List<Label> categories;
}
