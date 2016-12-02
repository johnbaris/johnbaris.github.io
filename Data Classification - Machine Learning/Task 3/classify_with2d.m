[~,confusion, class_rate,] = knn(test_classes(1,:),train_classes(1,:),train_features(:,1:2),test_features(:,1:2),1)
[~,confusion, class_rate, determinant] = gaussian_full(test_classes(1,:),train_classes(1,:),train_features(:,1:2),test_features(:,1:2),1)
[~,confusion, class_rate, determinant] = gaussian_lda(test_classes(1,:),train_classes(1,:),train_features(:,1:2),test_features(:,1:2),1)