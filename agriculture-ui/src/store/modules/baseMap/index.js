import { getFarmByTenant } from "@/api/farm/farm.js";
import { getLands } from "@/api/land/map";
import { getTenantId } from "@/utils/auth";
import { formatFarm, formatLandList, formatGrowthAnalysis } from "./help";
import promiseObj from "@/utils/promiseObj";


const useBaseMapStore = defineStore('baseMap', {
  state: () => ({
    farmName: "",
    farm: [],
    landList: [],
    growthAnalysis: null,
    landSegUrl: '',
    accumulatedTemperatureZone: null,
  }),
  actions: {
    // 获取信息
    getMapInfo() {
      const tenantId = getTenantId();
      return promiseObj({
        farmInfo: getFarmByTenant({ tenantId }),
        lands: getLands(),
      }).then((res) => {
        const { farmName, posVoList, growthAnalysis, landSegUrl, accumulatedTemperatureZone  } = res?.farmInfo?.value?.data || {};
        const landList = res?.lands?.value.data || [];

        this.farmName = farmName;
        this.growthAnalysis = formatGrowthAnalysis(growthAnalysis);
        this.accumulatedTemperatureZone = Number(accumulatedTemperatureZone);
        this.landSegUrl = landSegUrl;
        this.farm = formatFarm(posVoList);
        this.landList = formatLandList(landList);
      });
    }
  }
})

export default useBaseMapStore;