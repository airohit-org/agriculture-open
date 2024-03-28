import { getLandPage, getLands, queryRaiseCrops } from '@/api/land/map';
import { arr2json } from '@/utils';
import { formatlandPage, formatLands, formatRaiseCropsMap, initSearchOptions, PAGE_SIZE } from './help';

const useLandMapStore = defineStore('landMap', {
  state: () => ({

    arealist: [],
    arealistMap: [],
    searchOptions: [],
    landPage: [],
    pageNo: 1,
    total: 0,
    raiseCropsMap: {},
    raiseCrops: []
  }),
  actions: {
    loadAreaList() {
      return getLands().then(({ data = [] } = {}) => {
        const arealist = formatLands(data);
        // commit('SET_AREA_LIST', {
        //   arealist,
        //   arealistMap: arr2json(arealist, "id"),
        //   searchOptions: initSearchOptions(arealist)
        // })
        this.arealist = arealist
        this.arealistMap = arr2json(arealist, "id")
        this.searchOptions = initSearchOptions(arealist)
      })
    },
    loadPageLandSearch({ landName } = {}) {
      return getLandPage({
        pageNo: 1,
        pageSize: PAGE_SIZE,
        landName
      }).then(({ data: {
        list: landPage = [],
        total
      } = {} }) => {
        // commit('SEARCH_LAND_PAGE', {
        //   total,
        //   landPage: formatlandPage(landPage),
        // })
        this.total = total;
        this.landPage = formatlandPage(landPage);
        this.pageNo = 2;
      })
    },
    loadPageLand({ landName } = {}) {
      return getLandPage({
        pageNo: this.pageNo,
        pageSize: PAGE_SIZE,
        landName
      }).then(({ data: {
        list: landPage = [],
        total
      } = {} }) => {
        // commit('SET_LAND_PAGE', {
        //   total,
        //   landPage: formatlandPage(landPage),
        // })
        this.total = total;
        if (total === this.landPage.length) {
          return;
        }
        this.landPage = this.landPage.concat(landPage);
        this.pageNo = this.pageNo + 1;
      })
    },
    loadRaiseCrops() {
      return queryRaiseCrops().then(({ data: raiseCrops = [] } = {}) => {
        // commit('SET_RAISE_CROPS', { raiseCrops })
        this.raiseCrops = raiseCrops
        this.raiseCropsMap = formatRaiseCropsMap(raiseCrops)
      })
    }
  }
})


export default useLandMapStore;

