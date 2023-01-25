import com.plub.domain.base.DomainModel
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringDataResponseVo

data class RecommendationGatheringResponseVo (
    val plubbings : RecommendationGatheringDataResponseVo
) : DomainModel()