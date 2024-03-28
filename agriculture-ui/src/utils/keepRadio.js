import {debounceOneline as debounce } from '@/utils';

export const keepRatio = (dom, config, debounceFn = debounce) => {
  const {
    initW,
    initH,
    duration = 200,
    debounceDuration = 500,
  } = config || {
    duration: 200,
    debounceDuration: 500,
  };

  const rateW = initW;
  const rateH = initH;

  const setSize = () => {
    const { width, height } = dom.parentNode.getBoundingClientRect();
    const scale = Math.min(width / initW, height / initH);
    dom.style.transform = `translate(-50%, -50%) scale(${scale})`;
  };

  const init = () => {
    dom.parentNode.style.position = "relative";
    dom.style.position = "absolute";
    dom.style.left = "50%";
    dom.style.top = "50%";
    dom.style.transform = "translate(-50%, -50%) scale(1)";
    dom.style.aspectRatio = `${rateW} / ${rateH}`;
    dom.style.transition = `transform ease ${duration}ms`;
    setSize();
  };

  const onresize = debounceFn(setSize, debounceDuration);

  return {
    init,
    onresize,
  };
};
