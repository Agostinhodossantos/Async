package smartngo.async.com.data.database

import smartngo.async.com.core.EntityMapper
import smartngo.async.com.domain.model.User
import javax.inject.Inject


class CacheMapper
@Inject
constructor() : EntityMapper<UserCache, User> {
    override fun mapFromEntity(entity: UserCache): User {
         return User(
             id = entity.id,
             name = entity.name,
             location = entity.location
         )
    }

    override fun mapToEntity(domainModel: User): UserCache {
        return UserCache(
            id = domainModel.id,
            name = domainModel.name,
            location = domainModel.location
        )
    }

    override fun mapToList(list: List<UserCache>): List<User> {
        var tempList = mutableListOf<User>()
        list.forEach {
            tempList.add(User(it.id, it.name, it.location))
        }
        return tempList.toList()
    }

}