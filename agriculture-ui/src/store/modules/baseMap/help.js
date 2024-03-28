export const formatLatlng = (list = []) =>
  list.map(({ latitude, longitude }) => [latitude, longitude]);

export const formatFarm = (list = []) => formatLatlng(list);

export const formatLandList = (landList = []) =>
  landList.map(({ posVoList, color, ...item }) => ({
    color,
    pos: formatLatlng(posVoList),
    ...item,
  }));

function getValue(text) {
  return text;
}

export const formatGrowthAnalysis = (str) => {
  let obj;
  try {
    obj = eval(`getValue(${str})`);
    return obj;
} catch (err) {
    console.log(err);
  }

  return obj;
};
