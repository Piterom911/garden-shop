package com.predators.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity                                                     // помечает класс как таблицу в базе данных
@Table(name = "favorites")                                  // имя таблицы в БД
@Data                                                       // генерирует геттеры, сеттеры, toString, equals и hashCode
@NoArgsConstructor                                          // конструктор без аргументов
@AllArgsConstructor                                         // конструктор со всеми аргументами
@Builder                                                    // для паттерна Builder (удобен при создании объектов)

public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // автоинкремент
    private Long id;                                        //первичный ключ

    @ManyToOne                                              //многие избранные к одному пользователю
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne                                              //многие избранные к одному товару
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
