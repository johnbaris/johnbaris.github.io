function thehist = myhist(theimage,show)

  % set up bin edges for histogram
  edges = zeros(20,1);
  a = 0.05
  b = 0
  for i = 1:20
      edges(i) = b
      b = b + a
  end


  [R,C] = size(theimage);
  imagevec = reshape(theimage,1,R*C);      % turn image into long array
  thehist = histc(imagevec,edges)';        % do histogram
  if show > 0
      figure(show)
      clf
      pause(0.1)
      plot(edges,thehist)
  end
end