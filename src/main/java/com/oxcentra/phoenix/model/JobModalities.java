package com.oxcentra.phoenix.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name="modality")
@Data
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class JobModalities {
    public JobModalities(){}

    @Id
    @Column(name="id")
    private String modalityId;

    @Column(name="title")
    private String modalityTitle;
}
