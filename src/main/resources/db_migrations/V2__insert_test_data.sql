-- ACCOUNTS
insert into accounts (username, email, password) values
                                                     ('dimash', 'dimash@narxoz.kz', 'password123'),
                                                     ('duymeke', 'duymeke@narxoz.kz', 'password123');

insert into labels (name) values
                              ('backend'),
                              ('frontend'),
                              ('devops');

insert into work_items (title, text, is_completed, account_id) values
                                                                   ('workItem 1', 'textOne', false, 1),
                                                                   ('workItem 2', 'textTwo', true, 1),
                                                                   ('workItem 3', 'textThree', false, 2);

insert into work_item_labels (work_item_id, label_id) values
                                                          (1, 1),  -- backend
                                                          (1, 2),  -- frontend
                                                          (2, 1),  -- backend
                                                          (3, 3);  -- devops
