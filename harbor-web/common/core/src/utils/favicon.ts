export const changeFavicon = (link: string) => {
  let $favicon: any = document.querySelector('link[rel="icon"]');
  if ($favicon) {
    $favicon.href = link;
  } else {
    $favicon = document.createElement('link');
    $favicon.rel = 'icon';
    $favicon.href = link;
    document.head.appendChild($favicon);
  }
};
