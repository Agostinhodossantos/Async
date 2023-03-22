package smartngo.async.com.core


interface EntityMapper<Entity, DomainModel> {

    fun mapFromEntity(entity: Entity): DomainModel

    fun mapToEntity(domainModel: DomainModel): Entity

    fun mapToList(list: List<Entity>): List<DomainModel>
}