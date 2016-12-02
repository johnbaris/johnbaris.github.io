function plotDecisionBoundaries(X, true_labels, test_labels, label_names, classifier)
   %Inputs:
%    X : your data to be visualized, N x 2, where N is the number of datapoints you are plotting.
%    true_labels : N sized vector of the true classes of the N data points 
%    label_names : Cell array with the name of the labels, i.e. {'1', '2', '3', ..., '0'}
%    classifier : This is the function that you've programmed to classify. It can be k-NN, or any of the two Gaussian classifiers. Note that you can add arguments to plotDecisionBoundaries if you classifier function needs more arguments or adapt it as you want.
% 
%   We define here the colormap we will use to colour each of the 10 classes.
    cmap = [0.80369089,  0.61814689,  0.46674357;
        0.81411766,  0.58274512,  0.54901962;
        0.58339103,  0.62000771,  0.79337179;
        0.83529413,  0.5584314 ,  0.77098041;
        0.77493273,  0.69831605,  0.54108421;
        0.72078433,  0.84784315,  0.30039217;
        0.96988851,  0.85064207,  0.19683199;
        0.93882353,  0.80156864,  0.4219608 ;
        0.83652442,  0.74771243,  0.61853136;
        0.7019608 ,  0.7019608 ,  0.7019608];

    % Stepsize defines how fine-grained we want our grid. The small the
    % value, the more resolution the visualization will have, at the
    % expense of computational cost (we would need to classify more
    % data-points since the grid would be denser).
    stepSize = 0.05;
    
    x1range = (max(X(:,1)) - min(X(:,1)));
    x2range = (max(X(:,2)) - min(X(:,2)));
    
    x1plot = linspace(min(X(:,1)), max(X(:,1)), x1range/stepSize)';
    x2plot = linspace(min(X(:,2)), max(X(:,2)), x2range/stepSize)';
  
    % We obtain the grid vectors for the two dimensions.    
    [X1, X2] = meshgrid(x1plot, x2plot);

    % Concatenate them such that we can feed 'gridX' to your classifier.
    gridX = [X1(:), X2(:)];
    size(gridX)
    % Call here your classification method using the function you've coded to obtain the labels for each point
    % in the grid. Adapt this to you code!
    grid_labels = classifier(test_labels,true_labels,X,gridX,1);
    % Now we create the figure to visualize:
    figure;
    
    % This function will draw the decision boundaries
    [C,h] = contourf(x1plot(:), x2plot(:), reshape(grid_labels, length(x2plot),length(x1plot)));
    set(h,'LineColor','none')

    % Important calls to properly define the color map:
    colormap(cmap);

    % Range of our class labels for the color mapping.
    caxis([1 10]);
    
    hold on;
    
 
    % Plot the scatter plots grouped by their classes, with black border.
    scatters = gscatter(X(:,1),X(:,2),true_labels(1,:), [0,0,0], 'o', 10);
    
    % Fill in the color of each point according to the class labels.
    for n = 1:length(scatters)
          set(scatters(n), 'MarkerFaceColor', cmap(n,:));
    end
    
    legend(scatters,label_names);    
  
    hold off;
end