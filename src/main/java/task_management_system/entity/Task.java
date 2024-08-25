package task_management_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import task_management_system.enums.Priority;
import task_management_system.enums.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;

    @Basic(optional = false)
    @Column(name = "title")
    private String title;

    @Basic(optional = false)
    @Column(name = "description")
    private String description;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @Basic(optional = false)
    @Column(name = "create_date")
    private LocalDate createDate;

    @Basic(optional = false)
    @Column(name = "due_date")
    private LocalDate dueDate;

    @Basic(optional = false)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
