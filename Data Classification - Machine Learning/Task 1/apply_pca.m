[EVecs, EVals] = compute_pca(train_features)
E2 = EVecs(:,1:2);
Xpca = train_features*E2;
